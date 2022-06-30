from django.contrib.auth import authenticate
from django.contrib.auth.models import User
from django.contrib.sites.shortcuts import get_current_site
from django.core.mail import EmailMessage
from django.utils.encoding import force_bytes
from django.utils.http import urlsafe_base64_encode
from rest_framework import serializers
from rest_framework.authtoken.models import Token
from rest_framework.validators import UniqueValidator

from common.messages import message
from common.models import Profile
from common.tokens import account_activation_token


class RegisterSerializer(serializers.ModelSerializer):
    """회원가입(임시) Serializer"""
    email = serializers.EmailField(
        required=True,
        validators=[UniqueValidator(queryset=User.objects.all())],  # 이메일 중복 검증
        help_text='이메일(Unique)'
    )

    class Meta:
        model = User
        fields = ('username', 'email')

    def create(self, validated_data):
        username = validated_data['username']
        email = validated_data['email']

        user = User.objects.create_user(
            username=username,
            email=email)
        user.is_active = False
        user.save()

        # ----- 이메일 인증 -----
        current_site = get_current_site(self.context['request'])
        domain = current_site.domain
        uidb64 = urlsafe_base64_encode(force_bytes(user.pk))
        token = account_activation_token.make_token(user)
        message_data = message(domain, uidb64, token)

        mail_title = "MYN: 이메일 인증을 완료해 주세요."
        EmailMessage(mail_title, message_data, to=[email]).send()
        return user


class SigninSerializer(serializers.Serializer):
    """로그인 Serializer"""
    # 비밀번호에 write_only 옵션
    # 클라이언트->서버 역직렬화 가능, 서버->클라이언트 직렬화 불가능
    username = serializers.CharField(required=True, help_text='사용자 이름')
    password = serializers.CharField(required=True, write_only=True, help_text='비밀번호')

    def validate(self, data):
        user = authenticate(**data)
        if user:
            return Token.objects.get(user=user)  # 로그인 성공 시 토큰 반환
        raise serializers.ValidationError({'error': 'Unable to sign in with provided credentials.'})

    def update(self, instance, validated_data):
        pass

    def create(self, validated_data):
        pass


class ProfileSerializer(serializers.ModelSerializer):
    """프로필 Serializer"""
    class Meta:
        model = Profile
        fields = ('phone', 'address', 'from_social')
