package yakofinance;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Parser {

    public List<Data> parse(String path) throws IOException, ParseException {

        BufferedReader reader = new BufferedReader(new FileReader(
                path));

        String line = null;
        Scanner scanner = null;
        int index = 0;
        List<Data> empList = new ArrayList<>();
        line = reader.readLine();
        while ((line = reader.readLine()) != null) {
            Data emp = new Data();
            scanner = new Scanner(line);
            scanner.useDelimiter(",");
            while (scanner.hasNext()) {
                DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                String data =scanner.next();
                if (index == 0) {
                    Date value = fmt.parse(data);
                    emp.setDate(value);
                }
                else if (index == 1) {
                    emp.setCurrency(Double.parseDouble(data));
                }
                else if(index==2){
                    emp.setHight(Double.parseDouble(data));
                }
                index++;
            }
            index = 0;
            empList.add(emp);
        }

        reader.close();

        return empList;

    }

}