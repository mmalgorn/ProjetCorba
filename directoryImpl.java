package files;

import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import java.lang.*;
import java.util.*;
import java.io.*;

public class directoryImpl extends directoryPOA
{
    protected POA poa_;
    private int number_of_file;
    File currentDir;
    String path;
    String name;
    ArrayList<regular_fileImpl> alFile;
    ArrayList<directoryImpl> alDir;

    /*
     *  Constructeur d'un nouveau directory avec en parametre le nom du directory, la POA et le chemin avec lequel le directory sera créé
    */
    public directoryImpl(String n, POA poa,String p){
        this.path = p;
        currentDir = new File(p).mkdir();
        this.poa_ = poa;
        this.name = n;
        alFile =  new ArrayList<regular_fileImpl>();
        alDir = new ArrayList<directoryImpl>();
        this.path +="/"+this.name;
    }

    public String name(){
	   return this.name;
    }
    public String path(){
        return this.path;
    }

    public int number_of_file(){
	   return this.number_of_file;
    }

    public void open_regular_file(regular_fileHolder r, String name, mode m) throws invalid_type_file, no_such_file{

    }

    public void open_directory(directoryHolder f, String name) throws invalid_type_file, no_such_file{

    }

    public void create_regular_file(regular_fileHolder r, String name) throws already_exist{
        try{
            /*
             *  Parcour la liste de regular_fileImpl pour verifier que le fichier n'est pas déja existant
            */
            Iterator<regular_fileImpl> iter = alFile.iterator();
            while(iter.hasNext()){
                if(iter.next().name().equals(name)){
                    throw new already_exist();
                }
            }
            /*
             *  Création et allocation du nouveau fichier.
            */
            regular_fileImpl newFile = new regular_fileImpl(name,this.path);
            org.omg.CORBA.Object alloc = poa_.servant_to_reference(newFile);
            r.value = regular_fileHelper.narrow(alloc);

        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void create_directory(directoryHolder f, String name) throws already_exist{
        try{
            /*
             *  Parcour la liste de directoryImpl pour verifier que le fichier n'est pas déja existant
            */
            Iterator<directoryImpl> iter = alDir.iterator();
            while(iter.hasNext()){
                if(iter.next().name().equals(name)){
                    throw new already_exist();
                }
            }
            /*
             *  Création et allocation du nouveau direcory.
            */
            directoryImpl newDir = new directoryImpl(name,poa_,this.path);
            org.omg.CORBA.Object alloc = poa_.servant_to_reference(newDir);
            f.value = directoryHelper.narrow(alloc);
        }catch(Exception e){
            System.out.println(e);
        } 
    }

    public void delete_file(String name) throws no_such_file{
    }

    public int list_files(file_listHolder l){
	   return 0;
    }

}
