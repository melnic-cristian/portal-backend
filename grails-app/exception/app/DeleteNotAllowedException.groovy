package app

class DeleteNotAllowedException extends RuntimeException {
    DeleteNotAllowedException(String message) {
        super(message)
    }
}
