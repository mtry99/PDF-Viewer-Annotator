import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
class Rename {    
    public static void main(String[] args)
    {

        List<String> flags = Arrays.asList("-file", "-f", "-p", "-prefix", "-s", "-suffix", "-r", "-replace", "-h", "-help");
        LocalDateTime ldt = LocalDateTime.now();
        String date = DateTimeFormatter.ofPattern("MM-dd-yyyy").format(ldt);
        String time = DateTimeFormatter.ofPattern("HH-mm-ss").format(ldt);

        int file_flags = 0;
        int prefix_flags = 0;
        int suffix_flags = 0;
        int replace_flags = 0;
       //1. Check for -h|help command 
       for (int j=0; j < args.length; j++){
            
            if (args[j].equals("-file") || args[j].equals("-f")){
                file_flags++;
            }
            if (args[j].equals("-prefix") || args[j].equals("-p")){
                prefix_flags++;
            }
            if (args[j].equals("-suffix") || args[j].equals("-s")){
                suffix_flags++;
            }
            if (args[j].equals("-replace") || args[j].equals("-r")){
                replace_flags++;
            }

            
            if (args[j].equals("-h") || args[j].equals("-help")){
                System.out.println("(c) 2021 Maitry Mistry. Revised: June 4, 2021");
                System.out.println("Usage: ./rename [-option argument1 argument2 ...]");
                System.out.println();
                System.out.println("-f|file [filename]          :: file(s) to change.");
                System.out.println("-p|prefix [string]          :: rename [filename] so that it starts with [string].");
                System.out.println("-s|suffix [string]          :: rename [filename] so that it ends with [string].");
                System.out.println("-r|replace [str1] [str2]    :: rename [filename] by replacing all instances of [str1] with [str2].");
                System.out.println("-h|help                     :: print out this help and exit the program.");
                System.exit(0);
           }
            if (file_flags == 0 && prefix_flags == 0 && suffix_flags == 0 && replace_flags == 0){
                System.out.println("Invalid flag or value \"" + args[j] + "\" provided.");
                System.out.println("Run command -(h|help) for usage of Rename Utility");
                System.exit(0);
           }
       }

       if (file_flags > 1){
        System.out.println("Incorrect usage of Rename Utility: Cannot specify file flag (-f|file) more than once.");
        System.out.println("Run command -(h|help) for usage of Rename Utility");
        System.exit(0);
       }
       if (prefix_flags > 1){
        System.out.println("Incorrect usage of Rename Utility: Cannot specify prefix flag (-p|prefix) more than once.");
        System.out.println("Run command -(h|help) for usage of Rename Utility");
        System.exit(0);
       }
       if (suffix_flags > 1){
        System.out.println("Incorrect usage of Rename Utility: Cannot specify suffix flag (-s|suffix) more than once.");
        System.out.println("Run command -(h|help) for usage of Rename Utility");
        System.exit(0);
       }
       if (replace_flags > 1){
        System.out.println("Incorrect usage of Rename Utility: Cannot specify replace flag (-r|replace) more than once.");
        System.out.println("Run command -(h|help) for usage of Rename Utility");
        System.exit(0);
       }

       if (replace_flags < 1 && prefix_flags < 1 && suffix_flags < 1){
        System.out.println("Incorrect usage of Rename Utility: No valid flags provided.");
        System.out.println("Run command -(h|help) to get a list of valid flags and their usage.");
        System.exit(0);
       }
       



       //2. Get File names
        List<File> file_objects = new ArrayList<>();
        List<String> file_orignal = new ArrayList<>();
        List<String> file_rename = new ArrayList<>();
        int i = 0;
        while(i < args.length){

            if (args[i].equals("-file") || args[i].equals("-f")){
                if (i + 1 == args.length || ((i + 1 < args.length) && flags.contains(args[i + 1]))){
                    System.out.println("Incorrect usage of Rename Utility: -(f|file) requires a one or more filename values");
                    System.out.println("Run command -(h|help) for usage of Rename Utility");
                    System.exit(0);
                } 
                while ((i + 1 < args.length) && !(flags.contains(args[i + 1]))){
                    file_orignal.add(args[i + 1]);
                    File file = new File(args[i + 1]);
                    file_objects.add(file);
                    file_rename.add(file.getName());
                    i++;
                }
            }else if (args[i].startsWith("-") && !(flags.contains(args[i]))){
                System.out.println("Incorrect usage of Rename Utility: Invalid flag provided.");
                System.out.println("Run command -(h|help) for usage of Rename Utility");
                System.exit(0);
            }
            
            i++;
            
        }
        if (file_orignal.size() == 0){  //no files provided
            System.out.println("Incorrect usage of Rename Utility: No file name provided");
            System.out.println("Run command -(h|help) for usage of Rename Utility");
            System.exit(0);
        }

        //3. Apply transformation on filenames
        int k = 0;
        while (k < args.length){
            if (args[k].equals("-p") || args[k].equals("-prefix")){
               
                if (k + 1 == args.length || ((k + 1 < args.length) && flags.contains(args[k + 1]))){
                    System.out.println("Incorrect usage of Rename Utility: -(p|prefix) requires a one or more prefix values");
                    System.out.println("Run command -(h|help) for usage of Rename Utility");
                    System.exit(0);
                }
                
                while ((k + 1 < args.length) && !(flags.contains(args[k + 1]))){
                    if (args[k + 1].equals("@date")){ 
                        for(int y=0; y < file_rename.size(); y++){
                            file_rename.set(y, date + file_rename.get(y));
                        } 
                    
                    }else if (args[k + 1].equals("@time")){
                        for (int y=0; y < file_rename.size(); y++){
                            file_rename.set(y, time + file_rename.get(y));
                        }
                    }else if (!(args[k + 1].startsWith("-"))){
                        for(int y=0; y < file_rename.size(); y++){
                            file_rename.set(y,  args[k + 1] + file_rename.get(y));
                        }
                    }else if (args[k + 1].startsWith("-")){
                        System.out.println("Incorrect usage of Rename Utility: Invalid value provided for -(p|prefix) tag.");
                        System.out.println("Run command -(h|help) for usage of Rename Utility");
                        System.exit(0);
                    }  
                    k++;
                }
              
            }else if(args[k].equals("-s") || args[k].equals("-suffix")){
                if (k + 1 == args.length ||  ((k + 1 < args.length) && flags.contains(args[k + 1]))){
                    System.out.println("Incorrect usage of Rename Utility: -(s|suffix) requires one or more suffix values");
                    System.out.println("Run command -(h|help) for usage of Rename Utility");
                    System.exit(0);
                }
                while ((k + 1 < args.length) && !(flags.contains(args[k + 1]))){
                    if (args[k + 1].equals("@date")){
                        for(int y=0; y < file_rename.size(); y++){
                            file_rename.set(y, file_rename.get(y) + date);
                        }

                    }else if (args[k + 1].equals("@time")){
                        for(int y=0; y < file_rename.size(); y++){
                            file_rename.set(y, file_rename.get(y) + time);
                        }
                    }else if (!(args[k + 1].startsWith("-"))){
                        for(int y=0; y < file_rename.size(); y++){
                            file_rename.set(y, file_rename.get(y) + args[k + 1]);
                        }
                    }else if (args[k + 1].startsWith("-")){
                        System.out.println("Incorrect usage of Rename Utility: Invalid value provided for -(s|suffix) tag.");
                        System.out.println("Run command -(h|help) for usage of Rename Utility");
                        System.exit(0);
                    }   
                    k++;
               }
            }else if(args[k].equals("-r") || args[k].equals("-replace")){
                
                if (k + 3 < args.length && !(flags.contains(args[k + 3]))){
                    System.out.println("Incorrect usage of Rename Utility: -(r|rename) requires two values [str1] [str2]");
                    System.out.println("Run command -(h|help) for usage of Rename Utility");
                    System.exit(0);
                }

                if (k + 2 < args.length && !(flags.contains(args[k + 1])) && !(flags.contains(args[k + 2])) && (!(args[k + 1].startsWith("-"))) && (!(args[k + 2].startsWith("-")))){
                    for(int y=0; y < file_rename.size(); y++){
                        String file_name = file_rename.get(y);

                        if (!(file_name.contains(args[k+1]))){
                            System.out.println("Incorrect usage of Rename Utility: File " + file_orignal.get(y) + " doesn't contain string " + args[k + 1]+ " to be replaced." );
                            System.out.println("Run command -(h|help) for usage of Rename Utility");
                            System.exit(0);
                        }
                        String new_file_name = file_name.replace(args[k + 1], args[k + 2]);
                        file_rename.set(y, new_file_name);
                    }
                k = k + 2;
                }else{
                    System.out.println("Incorrect usage of Rename Utility: -(r|rename) requires two values [str1] [str2]");
                    System.out.println("Run command -(h|help) for usage of Rename Utility");
                    System.exit(0);
                }
            }
            
                k++;
        }
        //4. Replace file name
        for (int a=0; a < file_objects.size(); a++){
            String newFileName = file_objects.get(a).getParent() == null ? file_rename.get(a) : file_objects.get(a).getParent() + "\\" + file_rename.get(a);
            File rename = new File(newFileName);
            if (file_objects.get(a).renameTo(rename)){
                System.out.println("File Successfully Renamed: from \"" + file_orignal.get(a) + "\" to \"" + newFileName.replace("\\", "/") + "\"");
            }else {
                System.out.println("Operation Failed: Invalid FileName \"" + file_orignal.get(a) + "\". This file does not exist.");
            }
        }
    }
}