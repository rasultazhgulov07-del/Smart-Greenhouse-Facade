package greenhouse.facade;
import greenhouse.a.*;import greenhouse.b.*;import greenhouse.c.*;import greenhouse.workflow.*;
public class GreenhouseFacade {
 private final ScheduleService a;private final VentilationService b;private final NutrientService c;private final JobRepository jobs;
 public GreenhouseFacade(ScheduleService a,VentilationService b,NutrientService c,JobRepository jobs){this.a=a;this.b=b;this.c=c;this.jobs=jobs;}
 public Job start(String id,int moisture,int temp,int stock){Job j=pauseAfterA(id,moisture,temp,stock);return resume(j.id);}
 public Job pauseAfterA(String id,int moisture,int temp,int stock){if(id==null||!id.matches("[A-Za-z0-9_-]{1,30}")||jobs.find(id)!=null)throw new IllegalArgumentException("Invalid/duplicate ID");a.execute(id,moisture);Job j=new Job(id,moisture,temp,stock,Job.Stage.PENDING_B);jobs.save(j);return j;}
 public Job resume(String id){Job j=jobs.find(id);if(j==null)throw new IllegalArgumentException("Unknown ID");if(j.stage==Job.Stage.COMPLETED||j.stage==Job.Stage.COMPENSATED)return j;
 try{if(j.stage==Job.Stage.PENDING_B){b.execute(id,j.temp);j.stage=Job.Stage.PENDING_C;jobs.save(j);}if(j.stage==Job.Stage.PENDING_C){c.execute(id,j.stock);j.stage=Job.Stage.COMPLETED;jobs.save(j);}}
 catch(RuntimeException failure){ // read persisted state to determine compensation depth
 Ventilation v=b.find(id);if(v!=null&&v.status()==Ventilation.Status.ACTIVE)b.undo(id);a.undo(id);j.stage=Job.Stage.COMPENSATED;jobs.save(j);
 }return jobs.find(id);}
}
