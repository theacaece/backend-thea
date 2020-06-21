package edu.caece.app.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;

public class FileReferenceId implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column
  private String node;

  @Id
  @Column(length = 1024)
  private String path;

}
