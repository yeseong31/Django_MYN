from django.urls import path
from rest_framework_jwt.views import obtain_jwt_token, verify_jwt_token, refresh_jwt_token

from common.views import RegisterView, SigninView

urlpatterns = [
    # ----- 회원가입 -----
    path('signup/', RegisterView.as_view()),
    path('signin/', SigninView.as_view()),

    # ----- JWT Token -----
    # JWT Token 발행
    path('token/', obtain_jwt_token),
    # JWT Token 유효성 검증
    path('token/verify/', verify_jwt_token),
    # JWT Token 갱신
    path('token/refresh/', refresh_jwt_token),
]