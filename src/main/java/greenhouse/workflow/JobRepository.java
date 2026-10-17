package greenhouse.workflow;
import java.nio.file.*;import java.io.*;import java.util.*;
public class JobRepository {
 private final Path dir;public JobRepository(Path root){dir=root.resolve("jobs");}
 public void save(Job j){try{Files.createDirectories(dir);Properties p=new Properties();p.setProperty("moisture",""+j.moisture);p.setProperty("temp",""+j.temp);p.setProperty("stock",""+j.stock);p.setProperty("stage",j.stage.name());try(var out=Files.newOutputStream(dir.resolve(j.id+".properties"))){p.store(out,"job");}}catch(IOException e){throw new IllegalStateException(e);}}
 public Job find(String id){Path f=dir.resolve(id+".properties");if(!Files.exists(f))return null;try(var in=Files.newInputStream(f)){Properties p=new Properties();p.load(in);return new Job(id,Integer.parseInt(p.getProperty("moisture")),Integer.parseInt(p.getProperty("temp")),Integer.parseInt(p.getProperty("stock")),Job.Stage.valueOf(p.getProperty("stage")));}catch(IOException e){throw new IllegalStateException(e);}}
}
