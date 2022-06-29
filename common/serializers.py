import os

from django.contrib.auth import authenticate
from django.contrib.auth.models import User
from django.contrib.auth.password_validation import validate_password
from rest_framework import serializers
from rest_framework.authtoken.models import Token
from rest_framework.validators import UniqueValidator

from common.models import Profile


class TempRegisterSerializer(serializers.ModelSerializer):
    """회원가입(임시) Serializer"""
    email = serializers.EmailField(
        required=True,
        validators=[UniqueValidator(queryset=User.objects.all())],  # 이메일 중복 검증
    )

    class Meta:
        model = User
        fields = ('username', 'email')

    def create(self, validated_data):
        username = validated_data['username']
        email = validated_data['email']

        user = User.objects.create_user(
            username=username,
            email=email
        )
        user.set_password(os.environ['EMAIL_AUTH_PASSWORD'])
        user.is_active = False
        user.save()
        return user


class RegisterSerializer(serializers.ModelSerializer):
    """회원가입 Serializer"""
    password = serializers.CharField(
        write_only=True,
        required=True,
        validators=[validate_password]  # 비밀번호 검증
    )
    password2 = serializers.CharField(
        write_only=True,
        required=True
    )

    class Meta:
        model = User
        fields = ('username', 'password', 'password2', 'email')

    def validate(self, data):
        """비밀번호 일치 여부 확인"""
        if data['password'] != data['password2']:
            raise serializers.ValidationError({'password': "Password fields didn't match."})
        return data

    def create(self, validated_data):
        """CREATE 요청에 대해 create()를 overwrite 및 User/Token 생성"""
        email = validated_data['email']

        user = User.objects.create_user(
            username=validated_data['username'],
            email=email,
        )
        user.set_password(validated_data['password'])
        user.is_active = True
        user.save()

        Token.objects.create(user=user)  # 해당 계정에 대한 Token 발행
        return user


class SigninSerializer(serializers.Serializer):
    """로그인 Serializer"""

    # 비밀번호에 write_only 옵션
    # 클라이언트->서버 역직렬화 가능, 서버->클라이언트 직렬화 불가능
    username = serializers.CharField(required=True)
    password = serializers.CharField(required=True, write_only=True)

    def validate(self, data):
        user = authenticate(**data)
        if user:
            return Token.objects.get(user=user)  # 로그인 성공 시 토큰 반환
        raise serializers.ValidationError({'error': 'Unable to sign in with provided credentials.'})


class ProfileSerializer(serializers.ModelSerializer):
    """프로필 Serializer"""
    class Meta:
        model = Profile
        fields = ('phone', 'address', 'from_social')
