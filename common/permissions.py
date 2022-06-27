from rest_framework import permissions


class CustomReadOnly(permissions.BasePermission):
    """커스텀 권한
    GET: 누구나 (permissions.SAFE_METHODS)
    PUT/PATCH: 해당 User만
    """
    def has_object_permission(self, request, view, obj):
        if request.method in permissions.SAFE_METHODS:
            return True
        return obj.user == request.user
