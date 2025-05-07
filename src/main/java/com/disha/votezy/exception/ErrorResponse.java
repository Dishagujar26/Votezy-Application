package com.disha.votezy.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
      private int statusCode;
      private String messageString;  
}
//The ErrorResponse class is a data model used to represent structured error responses in the application.
//It ensures that when an error occurs, a consistent and readable JSON response is returned to the client.
