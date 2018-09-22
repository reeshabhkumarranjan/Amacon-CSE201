class ProductAlreadyExistsException extends Exception{

    public ProductAlreadyExistsException(String message){
        super(message);
    }
}

class InvalidPathException extends Exception{

    public InvalidPathException(String message){
        super(message);
    }
}

class ProductNotFoundException extends Exception{

    public ProductNotFoundException(String message){
        super(message);
    }
}

class FundsInsufficientException extends Exception{

    public FundsInsufficientException(String message){
        super(message);
    }
}