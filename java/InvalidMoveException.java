public class InvalidMoveException extends Exception {
   public InvalidMoveException() { 
   }
   public InvalidMoveException(String errorMessage) {
       super(errorMessage);
   }
}
