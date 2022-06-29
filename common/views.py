import traceback

from django.contrib.auth.models import User
from django.utils.encoding import force_str
from django.utils.http import urlsafe_base64_decode
from rest_framework import generics, status
from rest_framework.response import Response
from rest_framework.views import APIView

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
    """프로필"""
    queryset = Profile.objects.all()
    serializer_class = ProfileSerializer


class UserActivateView(APIView):
    def get(self, request, uidb64, token):
        """이메일 인증 및 사용자 활성화"""
        # 사용자 확인
        try:
            uid = force_str(urlsafe_base64_decode(uidb64))
            user = User.objects.get(pk=uid)
        except(TypeError, ValueError, OverflowError, User.DoesNotExist):
            user = None
        # Token 유효성 검증
        try:
            if user is not None and account_activation_token.check_token(user, token):
                user.is_active = True
                user.save()
                return Response({'message': f'{user.email} 계정이 활성화 되었습니다.'}, status=status.HTTP_200_OK)
            else:
                return Response({'message': '만료된 링크입니다.'}, status=status.HTTP_400_BAD_REQUEST)
        except Exception as e:
            print(traceback.format_exc())

