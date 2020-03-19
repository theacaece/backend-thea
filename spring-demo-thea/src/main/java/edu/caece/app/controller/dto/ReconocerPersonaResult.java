package edu.caece.app.controller.dto;

public class ReconocerPersonaResult {

  private boolean recognized = false;

  private boolean authorized = false;

  private String name;

  private String message;

  public ReconocerPersonaResult() {}

  public ReconocerPersonaResult(boolean recognized, boolean authorized, String name,
      String message) {
    super();
    this.recognized = recognized;
    this.authorized = authorized;
    this.name = name;
    this.message = message;
  }

  public boolean isRecognized() {
    return recognized;
  }

  public void setRecognized(boolean recognized) {
    this.recognized = recognized;
  }

  public boolean isAuthorized() {
    return authorized;
  }

  public void setAuthorized(boolean authorized) {
    this.authorized = authorized;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}
