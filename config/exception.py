"""커스텀 예외 핸들러"""
from django.http import JsonResponse
from rest_framework.views import exception_handler


def get_response(message='', result=None, status=False, status_code=200):
    """response 양식 작성"""

    if result is None:
        result = {}
    return {
        'message': message,
        'result': result,
        'status': status,
        'status_code': status_code
    }


def get_error_message(error_dict):
    """response 파싱"""

    field = next(iter(error_dict))
    response = error_dict[next(iter(error_dict))]

    if isinstance(response, dict):
        response = get_error_message(response)
    elif isinstance(response, list):
        response_message = response[0]
        if isinstance(response_message, dict):
            response = get_error_message(response_message)
        else:
            response = response[0]
    return response


def handle_exception(exc, context):
    """에러 메시지 및 상태 값 가공"""

    error_response = exception_handler(exc, context)
    if error_response is not None:
        error = error_response.data

        if error and isinstance(error, list):
            if isinstance(error[0], dict):
                error_response.data = get_response(
                    message=get_error_message(error),
                    status_code=error_response.status_code)
            elif isinstance(error[0], str):
                error_response.data = get_response(
                    message=error[0],
                    status_code=error_response.status_code)

        if isinstance(error, dict):
            error_response.data = get_response(
                message=get_error_message(error),
                status_code=error_response.status_code)

    return error_response


class ExceptionMiddleware(object):
    """ExceptionMiddleware를 통해 에러 사항에 대한 응답 처리"""

    def __init__(self, get_resp):
        self.get_response = get_resp

    def __call__(self, request):
        response = self.get_response(request)

        if response.status_code == 500:
            response = get_response(
                message='Internal server error, please try again later',
                status_code=response.status_code)
            return JsonResponse(response, status=response['status_code'])

        if response.status_code == 404 and 'Page not found' in str(response.content):
            response = get_response(
                message='Page not found, invalid url',
                status_code=response.status_code)
            return JsonResponse(response, status=response['status_code'])

        return response
