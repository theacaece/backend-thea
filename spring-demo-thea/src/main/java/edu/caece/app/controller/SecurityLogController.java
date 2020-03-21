package edu.caece.app.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import edu.caece.app.domain.SecurityLog;
import edu.caece.app.repository.SecurityLogRepository;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class SecurityLogController {

  protected final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private SecurityLogRepository securityLogController;

  @RequestMapping(method = RequestMethod.GET, path = "/securityLogs")
  public List<SecurityLog> getSecurityLogs(@RequestParam(required = false) Long afterId,
      @RequestParam(required = false) Integer limit) {
    return securityLogController.findLogsFrom(afterId != null ? afterId : -1,
        limit != null ? limit : 20);
  }

}
