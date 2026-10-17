package greenhouse.b;
public class VentilationService {
 private final ClimateRule rule;private final VentilationRepository repo;
 public VentilationService(ClimateRule rule,VentilationRepository repo){this.rule=rule;this.repo=repo;}
 public void execute(String id,int input){if(!rule.allowed(input))throw new IllegalArgumentException("b validation failed");Ventilation e=new Ventilation(id);e.activate();repo.save(e);}
 public void undo(String id){Ventilation e=repo.find(id);if(e!=null&&e.status()==Ventilation.Status.ACTIVE){e.stop();repo.save(e);}}
 public Ventilation find(String id){return repo.find(id);}
}
