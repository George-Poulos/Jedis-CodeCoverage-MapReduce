import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;

import com.sun.corba.se.spi.ior.Writeable;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.*;


/**
 * Class that holds classes Map and Reduce that implements hadoop mapReduce to parse CSV file, map it to key-values and then reduce it to a list where
 * all tests for a line show up next to each other.
 */
public class MapReduce
{

    public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text>
    {
        private Text test = new Text();
        private Text lineNumber = new Text();

        /**
         * map function that takes the csv as input and parses it line by line
         * @param key - Key
         * @param value - Value
         * @param output - output variable
         * @param reporter - *
         * @throws IOException - throw and exception
         */
        public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter)
                throws IOException
        {
            String line = value.toString();
            String values[] = line.split(";");
            lineNumber.set("");
            test.set("");
            if(values.length > 0) {
                lineNumber.set(values[0]);
            }
            if(values.length > 1) {
                test.set(values[1]);
            }
            output.collect(lineNumber, test);
        }
    }

    public static class Reduce extends MapReduceBase implements Reducer<Text, Text, Text, Text>
    {
        /**
         *
         * @param key - key
         * @param values - values
         * @param output - output variable
         * @param reporter - report
         * @throws IOException
         */
        @Override
        public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter)
                throws IOException
        {
            List<String> tests = new ArrayList<>();
            Text value = new Text();

            for (Iterator<Text> it = values; it.hasNext(); )
            {
                Text item = it.next();
                tests.add(item.toString());
            }

            value.set(String.join(", ", tests));
            output.collect(key, value);
        }
    }

    public static class IntComparator extends WritableComparator {

        public IntComparator() {
            super(IntWritable.class);
        }

        @Override
        public int compare(byte[] b1, int s1, int l1,
                           byte[] b2, int s2, int l2) {

            Integer v1 = ByteBuffer.wrap(b1, s1, l1).getInt();
            Integer v2 = ByteBuffer.wrap(b2, s2, l2).getInt();

            return v1.compareTo(v2) * (-1);
        }
    }

    public static void main(String[] args) throws Exception
    {
        JobConf conf = new JobConf(MapReduce.class);

        conf.setJobName("Software Testing Parallelization");
        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(Text.class);
        conf.setMapperClass(Map.class);
        conf.setCombinerClass(Reduce.class);
        conf.setReducerClass(Reduce.class);

        FileInputFormat.setInputPaths(conf, new Path(args[0]));
        FileOutputFormat.setOutputPath(conf, new Path(args[1]));

        JobClient.runJob(conf);
    }



}