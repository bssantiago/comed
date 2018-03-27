package com.mhc.exceptions.mapper;

import com.mhc.exceptions.converter.ErrorResponseConverter;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BaseInternalServerErrorHandler implements ExceptionMapper<Exception> {
	private final Logger LOGGER = Logger.getLogger(BaseInternalServerErrorHandler.class);

	@Override
	public Response toResponse(Exception exception) {
		LOGGER.error("General Exception occurred during REST service call.", exception);

        int status = Status.INTERNAL_SERVER_ERROR.getStatusCode();
        String message="A general server error has occurred. Please try again at a later time.";

        String[] messageParts = null;
        if(exception.getMessage()!=null){
			message=exception.getMessage();
		}

        try {
            if(StringUtils.isNotBlank(message) && (messageParts=message.split("\\|\\|")).length>1){
                status = Integer.valueOf(messageParts[0]);
                message = messageParts[1];
            }
        } catch (Exception ex) {
            LOGGER.error("Exception message didn't have status code before pipes.", exception);
        }

		ErrorResponseConverter errorResponseConverter = new ErrorResponseConverter(status,message);
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorResponseConverter)
				.type(MediaType.APPLICATION_JSON).build();
	}
}
