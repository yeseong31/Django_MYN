from django.contrib.auth.models import User
from django.test import TestCase


class SigninTest(TestCase):
    def setUp(self) -> None:
        self.username = 'TEST'
        self.email = 'test@test.com'
        self.password = '!TestTest13?'
        self.password2 = '!TestTest13?'
        self.user = User(username=self.username, password=self.password, email=self.email)

    def test_model_can_create_an_user(self):
        """테스트 user 생성 전후의 데이터 수 비교"""

        old_count = User.objects.count()
        self.user.save()
        new_count = User.objects.count()
        self.assertNotEqual(old_count, new_count)
