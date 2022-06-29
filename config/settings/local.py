import dj_database_url

from . import my_settings
from .base import *

# SECURITY WARNING: keep the secret key used in production secret!
SECRET_KEY = my_settings.SECRET_KEY

# SECURITY WARNING: don't run with debug turned on in production!
DEBUG = True

ALLOWED_HOSTS = []


# Database
# https://docs.djangoproject.com/en/4.0/ref/settings/#databases

DATABASES = my_settings.DATABASES

db_from_env = dj_database_url.config(conn_max_age=500)
DATABASES['default'].update(db_from_env)
