package greenhouse.a;
public class ScheduleService {
 private final SoilRule rule;private final ScheduleRepository repo;
 public ScheduleService(SoilRule rule,ScheduleRepository repo){this.rule=rule;this.repo=repo;}
 public void execute(String id,int input){if(!rule.allowed(input))throw new IllegalArgumentException("a validation failed");Schedule e=new Schedule(id);e.reserve();repo.save(e);}
 public void undo(String id){Schedule e=repo.find(id);if(e!=null&&e.status()==Schedule.Status.RESERVED){e.cancel();repo.save(e);}}
 public Schedule find(String id){return repo.find(id);}
}
