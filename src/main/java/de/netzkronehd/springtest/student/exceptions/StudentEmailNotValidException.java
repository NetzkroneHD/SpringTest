package de.netzkronehd.springtest.student.exceptions;

public class StudentEmailNotValidException extends IllegalStateException {

    public StudentEmailNotValidException(String s) {
        super(s);
    }


}
