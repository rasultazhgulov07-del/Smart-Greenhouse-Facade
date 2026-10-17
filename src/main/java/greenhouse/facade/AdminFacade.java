package greenhouse.facade;
import greenhouse.a.*;import greenhouse.b.*;import greenhouse.c.*;import greenhouse.workflow.*;
public class AdminFacade {
 private final ScheduleService a;private final VentilationService b;private final NutrientService c;private final JobRepository jobs;
 public AdminFacade(ScheduleService a,VentilationService b,NutrientService c,JobRepository jobs){this.a=a;this.b=b;this.c=c;this.jobs=jobs;}
 public String inspect(String id){Job j=jobs.find(id);if(j==null)return "Unknown ID";Schedule x=a.find(id);Ventilation y=b.find(id);Nutrients z=c.find(id);return id+" workflow="+j.stage+" A="+(x==null?"-":x.status())+" B="+(y==null?"-":y.status())+" C="+(z==null?"-":z.status());}
 public void compensate(String id){Job j=jobs.find(id);if(j==null)throw new IllegalArgumentException("Unknown ID");if(j.stage==Job.Stage.COMPLETED)throw new IllegalStateException("Cannot undo actual nutrient dispensing");if(j.stage==Job.Stage.COMPENSATED)return;b.undo(id);a.undo(id);j.stage=Job.Stage.COMPENSATED;jobs.save(j);}
}
