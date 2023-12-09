package common;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class pactcreationprogram {

    public static void main(String[] args ) throws IOException {
        //FILL THE DETAILS BELOW
        //profile setup
        //******************
        String classname = "Create_RO_Selection_ORS_Consumer";
        String providername="Create_RO_Selection_CMDS_Provider_Post";
        String consumername="Create_RO_Selection_ORS_Consumer_Post";
        String endpoint="/v1/booking/organisation-selection";
        String inputjson="ORS_To_CMDS_Req.txt";
        String requeststatment ="Booking publishes organisation-selection details to Ors";
        String responsestatment ="Ors sends acknowledgement for organisation-selection";

        //*******************************************************************************
        //add the import statments
        //********************************************************************************
        List<String> dataout = new ArrayList<String>();

        String myfile1 = "";
        BufferedReader abc1 = new BufferedReader(new FileReader(myfile1));
        String s1;
        while ((s1 = abc1.readLine()) != null)
        {  dataout.add(s1);  }

        //add profiles
        String var0 =" ";
        dataout.add(var0);
        String var1 = "public class " + classname + "  {";
        dataout.add(var1);
        String var01 =" ";
        dataout.add(var01);
        String var2 ="public static final String providerServiceName = \"" + providername + "\"" + ";";
        dataout.add(var2);
        String var3 ="public static final String consumerServiceName = \"" + consumername + "\""+ ";";;
        dataout.add(var3);
        String var4 ="public static int providerServicePort = 8110;";
        dataout.add(var4);
        String var5 = "public static String providerUrl_POST_Booking = \"http://localhost:\" + providerServicePort + \""+ endpoint + "\""+ ";";;
        dataout.add(var5);
        String var6 ="public String postbody = new String(Files.readAllBytes(Paths.get(\"src/Resources/"+ inputjson + "\")), StandardCharsets.UTF_8);";
        dataout.add(var6);
        String var7 ="public "+classname + "()"+ " throws IOException {   }";
        dataout.add(var7);

        String myfile2 = "";
        BufferedReader abc2 = new BufferedReader(new FileReader(myfile2));
        String s2;
        while ((s2 = abc2.readLine()) != null)
        {  dataout.add(s2);  }

        String Var = "";
        String AddVar = "";


        //*************************************************************************************
        //convert PACT Json to DSL pact
        //***********************************************************************************

        String myfile = "";
        BufferedReader abc = new BufferedReader(new FileReader(myfile));
        List<String> data = new ArrayList<String>();
        String s;
        while ((s = abc.readLine()) != null)
        {  data.add(s);  }

      // System.out.println(data);

        for (int i = 0; i < data.size()-1; i++)
        {

            Var = data.get(i).trim();
           // System.out.println("token:"+ Var);
            if(Var.equals("{") && i != 0)
            {
                AddVar = "          .object()";
                dataout.add(AddVar);

            }
            if(Var.equals("}"))
            {
                AddVar = "          .closeObject()";
                dataout.add(AddVar);
            }

            int index1 = Var.indexOf("{");
            int index2 = Var.indexOf("}");
            int index3 = Var.indexOf("[");
            int index4 = Var.indexOf("]");
            //System.out.println("index1: " + index1 + "index2: " + index2 +"index3: " + index3 + "index4: " + index3   );
            if (index1 == -1 && index2== -1 && index3 == -1 && index4 == -1)
            {
                String someString = Var;
                char someChar = '"';
                int numberofComma = 0;

                for (int j = 0; j < someString.length(); j++)
                {
                    if (someString.charAt(j) == someChar)
                    {
                        numberofComma++;
                    }
                }
             //  System.out.println("numberofcomma:" + numberofComma);
               if(numberofComma == 4) {
                   String repString1 = Var.replace(',', ' ');
                   String repString2 = repString1.replace(':', ',');
                   String repString = repString2.trim();
                   AddVar = "          .stringType(" + repString + ")";
                   dataout.add(AddVar);
                   numberofComma = 0;

               }
                if(numberofComma == 2) {
                    String repString1 = Var.replace(',', ' ');
                    String repString2 = repString1.replace(':', ',');
                    String repString = repString2.trim();
                    AddVar = "          .numberType(" + repString + ")";
                    dataout.add(AddVar);
                    numberofComma = 0;
                }

            }

            if (index1 > 0)
            {
                String repString1 = Var.replace(':',' ');
                String repString2 = repString1.replace('{',' ');
                String repString = repString2.trim();
                AddVar = "          .object(" + repString + ")";
                dataout.add(AddVar);
            }
            if(Var.equals("},"))
            {
                AddVar = "          .closeObject()";
                dataout.add(AddVar);
            }


            if(index3 > 0) // Array creation
            {
                String repString1 = Var.replace(':',' ');
                String repString2 = repString1.replace('[',' ');
                String repString = repString2.trim();
                AddVar = "          .array(" + repString + ")";
                dataout.add(AddVar);
            }
            if(Var.equals("["))
            {
                AddVar = "          .array()";
                dataout.add(AddVar);
            }
            if(Var.equals("],"))
            {
                AddVar = "          .closeArray()";
                dataout.add(AddVar);
            }
            if(Var.equals("]"))
            {
                AddVar = "          .closeArray()";
                dataout.add(AddVar);
            }
        }

        String var8 = "          .asBody();";
        dataout.add(var8);
        String var9 ="  DslPart resBody = new PactDslJsonBody()";
        dataout.add(var9);

        //***************************************
        //add the response boy DSL statments
        //*************************************
        String myfile3 = "";
        BufferedReader abc3 = new BufferedReader(new FileReader(myfile3));
        List<String> data3 = new ArrayList<String>();
        String s3;
        while ((s3 = abc3.readLine()) != null)
        {  data3.add(s3);  }

       // System.out.println(data3);

        for (int k = 0; k < data3.size()-1; k++)
        {

            Var = data3.get(k).trim();
        //    System.out.println("token:"+ Var);
            if(Var.equals("{") && k != 0)
            {
                AddVar = "          .object()";
                dataout.add(AddVar);

            }
            if(Var.equals("}"))
            {
                AddVar = "          .closeObject()";
                dataout.add(AddVar);
            }

            int index1 = Var.indexOf("{");
            int index2 = Var.indexOf("}");
            int index3 = Var.indexOf("[");
            int index4 = Var.indexOf("]");
         //   System.out.println("index1: " + index1 + "index2: " + index2 +"index3: " + index3 + "index4: " + index3   );
            if (index1 == -1 && index2== -1 && index3 == -1 && index4 == -1)
            {
                String someString = Var;
                char someChar = '"';
                int numberofComma = 0;

                for (int m = 0; m < someString.length(); m++)
                {
                    if (someString.charAt(m) == someChar)
                    {
                        numberofComma++;
                    }
                }
              //  System.out.println("numberofcomma:" + numberofComma);
                if(numberofComma == 4) {
                    String repString1 = Var.replace(',', ' ');
                    String repString2 = repString1.replace(':', ',');
                    String repString = repString2.trim();
                    AddVar = "          .stringType(" + repString + ")";
                    dataout.add(AddVar);
                    numberofComma = 0;

                }
                if(numberofComma == 2) {
                    String repString1 = Var.replace(',', ' ');
                    String repString2 = repString1.replace(':', ',');
                    String repString = repString2.trim();
                    AddVar = "          .numberType(" + repString + ")";
                    dataout.add(AddVar);
                    numberofComma = 0;
                }

            }

            if (index1 > 0)
            {
                String repString1 = Var.replace(':',' ');
                String repString2 = repString1.replace('{',' ');
                String repString = repString2.trim();
                AddVar = "          .object(" + repString + ")";
                dataout.add(AddVar);
            }
            if(Var.equals("},"))
            {
                AddVar = "          .closeObject()";
                dataout.add(AddVar);
            }


            if(index3 > 0) // Array creation
            {
                String repString1 = Var.replace(':',' ');
                String repString2 = repString1.replace('[',' ');
                String repString = repString2.trim();
                AddVar = "          .array(" + repString + ")";
                dataout.add(AddVar);
            }
            if(Var.equals("["))
            {
                AddVar = "          .array()";
                dataout.add(AddVar);
            }
            if(Var.equals("],"))
            {
                AddVar = "          .closeArray()";
                dataout.add(AddVar);
            }
            if(Var.equals("]"))
            {
                AddVar = "          .closeArray()";
                dataout.add(AddVar);
            }
        }

        String var10 = "          .asBody();";
        dataout.add(var10);

        String var11 = "  ";
        dataout.add(var11);
        String var12 = "    return builder";
        dataout.add(var12);
        String var13 ="             .given(\""+ requeststatment + "\")";
        dataout.add(var13);

        String var14 ="             .uponReceiving(\""+ responsestatment + "\")";
        dataout.add(var14);
        String var15 ="             .method(\"POST\")";
        dataout.add(var15);
        String var16 ="             .headers(headers)";
        dataout.add(var16);
        String var17 ="             .body(reqBody)";
        dataout.add(var17);
        String var18 ="             .path(\"" + endpoint + "\")";
        dataout.add(var18);
        String var19 ="             .willRespondWith()";
        dataout.add(var19);
        String var20 ="             .status(202)";
        dataout.add(var20);
        String var21 ="             .headers(headers)";
        dataout.add(var21);
        String var22 ="             .body(resBody)";
        dataout.add(var22);
        String var23 ="             .toPact();";
        dataout.add(var23);
        String var24 ="            }";
        dataout.add(var24);

        String myfile5 = "";
        BufferedReader abc5 = new BufferedReader(new FileReader(myfile5));
        String s5;
        while ((s5 = abc5.readLine()) != null)
        {  dataout.add(s5);  }


        //**********************************
        // print the output file
        //******************************
        FileWriter myWriter = new FileWriter("");

        for (int i = 0; i < dataout.size(); i++)
            {
            System.out.println(dataout.get(i));
                myWriter.write(dataout.get(i));
                myWriter.write("\n");
            }

        myWriter.close();

        abc.close();
    }
}

