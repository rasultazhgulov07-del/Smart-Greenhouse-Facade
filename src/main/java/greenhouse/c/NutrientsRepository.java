package greenhouse.c;
import java.nio.file.*;import java.io.*;import java.util.*;
public class NutrientsRepository {
 private final Path dir;public NutrientsRepository(Path root){dir=root.resolve("c");}
 public void save(Nutrients e){try{Files.createDirectories(dir);Properties p=new Properties();p.setProperty("status",e.status().name());try(var out=Files.newOutputStream(dir.resolve(e.id()+".properties"))){p.store(out,"status");}}catch(IOException x){throw new IllegalStateException(x);}}
 public Nutrients find(String id){Path f=dir.resolve(id+".properties");if(!Files.exists(f))return null;try(var in=Files.newInputStream(f)){Properties p=new Properties();p.load(in);return new Nutrients(id,Nutrients.Status.valueOf(p.getProperty("status")));}catch(IOException x){throw new IllegalStateException(x);}}
}
