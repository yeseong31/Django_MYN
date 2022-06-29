import os
import traceback

from django.contrib.auth.models import User
from django.contrib.sites.shortcuts import get_current_site
from django.core.mail import EmailMessage
from django.utils.encoding import force_str, force_bytes
from django.utils.http import urlsafe_base64_decode, urlsafe_base64_encode
from rest_framework import generics, status
from rest_framework.response import Response
from rest_framework.views import APIView

from common.messages import message
from common.models import Profile
from common.serializers import RegisterSerializer, SigninSerializer, ProfileSerializer
from common.tokens import account_activation_token


class RegisterView(generics.CreateAPIView):
    """회원가입: POST"""
    queryset = User.objects.all()
    serializer_class = RegisterSerializer


class SigninView(generics.GenericAPIView):
    """로그인: POST"""
    serializer_class = SigninSerializer

    def post(self, request):
        serializer = self.get_serializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        token = serializer.validated_data  # validate()의 return 값
        return Response({'token': token.key}, status=status.HTTP_200_OK)


class ProfileView(generics.RetrieveUpdateAPIView):
    """프로필 생성"""
    queryset = Profile.objects.all()
    serializer_class = ProfileSerializer


class UserActivateView(APIView):
    """이메일 인증 및 사용자 활성화"""
    def get(self, request, uidb64, token):
        # 사용자 확인
        try:
            uid = force_str(urlsafe_base64_decode(uidb64))
            user = User.objects.get(pk=uid)
        except(TypeError, ValueError, OverflowError, User.DoesNotExist):
            user = None
        # Token 유효성 검증
        try:
            if user is not None and account_activation_token.check_token(user, token):
                user.delete()
                return Response({'message': '인증되었습니다.'}, status=status.HTTP_200_OK)
            else:
                return Response({'message': '만료된 링크입니다.'}, status=status.HTTP_400_BAD_REQUEST)
        except Exception as e:
            print(traceback.format_exc())


class CheckIDView(APIView):
    """아이디 중복 확인"""
    def post(self, request):
        username = request.data['userid']
        if User.objects.filter(username=username):
            return Response({'message': '이미 존재하는 사용자입니다.'}, status=status.HTTP_400_BAD_REQUEST)
        return Response({'message': '사용 가능한 아이디입니다.'}, status=status.HTTP_200_OK)


class CheckEmailView(APIView):
    """이메일 인증"""
    def post(self, request):
        username = request.data['userid']
        email = request.data['email']

        user = User.objects.create_user(username=username, email=email)

        # ----- 이메일 인증 -----
        current_site = get_current_site(request)
        domain = current_site.domain
        uidb64 = urlsafe_base64_encode(force_bytes(user.pk))
        token = account_activation_token.make_token(user)
        message_data = message(domain, uidb64, token)

        mail_title = "MYN: 이메일 인증을 완료해 주세요."
        EmailMessage(mail_title, message_data, to=[email]).send()

        return Response({'message': '이메일 인증 링크를 발송하였습니다. 이메일을 확인해 주세요.'}, status=status.HTTP_200_OK)

