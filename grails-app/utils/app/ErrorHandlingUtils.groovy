package app
;

import grails.core.GrailsApplication
import org.springframework.http.HttpStatus

class ErrorHandlingUtils {

    static void handleErrors(GrailsApplication grailsApplication, def authorDTO, def controller) {
        def errorMessages = authorDTO.errors.allErrors.collect { error ->
            controller.message(error: error, messageSource: grailsApplication.mainContext.getBean('messageSource'))
        }
        controller.respond([errors: errorMessages], status: HttpStatus.UNPROCESSABLE_ENTITY, formats: ['json'])
    }
}
