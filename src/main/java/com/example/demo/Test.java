package com.example.demo;

import  org.apache.commons.io.IOUtils;

import java.io.*;
import java.sql.SQLException;
import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;

public class Test {

    private String clobToString(java.sql.Clob data){
        String clobContent = "";
        if (data == null) {
            return clobContent;
        }
        try {
            Reader reader = data.getCharacterStream();
            StringWriter writer = new StringWriter();
            IOUtils.copy(reader, writer);
            clobContent = writer.toString();
            return clobContent;

        }catch(IOException | SQLException e){
            e.printStackTrace();
//            logger.error("Error occurred during String conversion "+e);
        }
        return clobContent;
    }

    public static void main(String[] args) throws SQLException {

        Test test = new Test();
        Object object="{\"sections\":[{\"sectionIndex\":\"0\",\"sectionId\":\"SEC_RECHARGE\",\"sectionName\":\"\",\"sectionName_ar\":\"\",\"sectionVisibility\":\"true\"},{\"sectionIndex\":\"1\",\"sectionId\":\"SEC_USAGE\",\"sectionName\":\"My Usage\",\"sectionName_ar\":\"استخدامي\",\"sectionVisibility\":\"true\"},{\"sectionIndex\":\"2\",\"sectionId\":\"SEC_SWYP_BUDDY\",\"sectionName\":\"\",\"sectionName_ar\":\"\",\"sectionVisibility\":\"true\"},{\"sectionIndex\":\"3\",\"sectionId\":\"SEC_EVENTS_PROMO\",\"sectionName\":\"\",\"sectionName_ar\":\"\",\"sectionVisibility\":\"false\"},{\"sectionIndex\":\"4\",\"sectionId\":\"SEC_WHATS_HAPPENING\",\"sectionName\":\"Whats's Happening?\",\"sectionName_ar\":\"كل ما هو جديد\",\"sectionVisibility\":\"true\"},{\"sectionIndex\":\"5\",\"sectionId\":\"SEC_PACKS\",\"sectionName\":\"Packs For You\",\"sectionName_ar\":\"عروض تناسبك\",\"sectionVisibility\":\"true\"},{\"sectionIndex\":\"6\",\"sectionId\":\"SEC_INVITE_FREIND\",\"sectionName\":\"\",\"sectionName_ar\":\"\",\"sectionVisibility\":\"true\"}]}";
        java.sql.Clob clobevalue = new javax.sql.rowset.serial.SerialClob(((String)object).toCharArray());
//        java.sql.Clob data = (java.sql.Clob) object;
            test.clobToString(clobevalue);



//        Map Interface
//        SortedMap Interface implements TreeMap (elements sorted a/c to keys)
//        FlatMap [ [1,2,3],[4,5,6],[7,8,9] ] output [ 1,2,3,4,5,6,7,8,9 ]
//        HashMap Key Value pair, doesn't maintains order thread not safe
//        LinkedHashMap key value pair maintains order
//        Hashtable thread safe but doesn't allows null

//        Queue Interface add remove peek poll(remove head and return)
//        Either linked list or priority queue but for thread safe PriorityBlockingQueue
//        Stack Class

//        ArrayList contiguous when lots of manipulation List interface
//        Linked List non contiguous ease of insertions and deletions, List + Dequque (aage , peeche dono)
//        Array vs ArrayList array can contain primitive type while arraylist can't
//        also in arraylist elements are not stored in contiguous mem locations rather references of their
//        are saved
//        private List finishingOrder;
//        Make an ArrayList to hold RaceCar objects to determine winners
//        finishingOrder = Collections.synchronizedList(new ArrayList(numberOfRaceCars)

//        In pessimistic locking, the object is locked when it is initially accessed for the first time in a
//        given transaction. ... In optimistic locking, the object is not locked when it is accessed for the
//        first time in the transaction. Instead, its state (generally the version number) is saved.

        Map<String, Integer> hm
                = new HashMap<String, Integer>();

        hm.put("a", new Integer(100));
        hm.put("b", new Integer(200));
        hm.put("c", new Integer(300));
        hm.put("d", new Integer(400));

        hm.entrySet().stream().forEach(System.out::println);
        hm.entrySet().stream().forEach(k -> {
            k.getKey();
            k.getValue();
        });


        // Traversing through the map
        for (Map.Entry<String, Integer> me : hm.entrySet()) {
            System.out.print(me.getKey() + ":");
            System.out.println(me.getValue());
        }

        Map<String, Integer> map
                = new TreeMap<>();

        // Map to store the elements
        Map<String, String> treemap =
                new TreeMap<String, String>(Collections.reverseOrder());

        map.put("a", 100);
        map.put("b", 30);
        map.put("c", 20);
        map.put("D", 20);


        for (Map.Entry<String, Integer> e : map.entrySet())
            System.out.println(e.getKey() + " "
                    + e.getValue());

        Queue<Integer> q
                = new LinkedList<>();


//        boxed is used b/c u cannot convert direct to streams
        Optional<Integer> max = IntStream.of(1, 2, 3, 4, 5)
                .boxed()
                .max(Integer::compareTo);

//        IntStream.range(0, numbers.length)
//                .forEach(index -> {
//                    doubleNumbers[index] = numbers[index] * 2;
//                    tripleNumbers[index] = numbers[index] * 3;
//                });

//        Streams divided into intermediate(filter) / terminal(foreach stream used) operations
//        Streams are wrappers around a data source, allowing us to operate with that data source and making
//        bulk processing convenient and fast
//        forEach     empList.stream().forEach(e -> e.salaryIncrement(10.0));
//        map List<Employee> employees = Stream.of(empIds).map(employeeRepository::findById).collect(Collectors.toList());
//        filter List<Employee> employees = Stream.of(empIds).map(employeeRepository::findById).filter(e -> e != null).filter(e -> e.getSalary() > 200000).collect(Collectors.toList());
//        findFirst
//        toArray
//        flatMap (flattens the data structure)
//        List<List<String>> namesNested = Arrays.asList(Arrays.asList("Jeff", "Bezos"), Arrays.asList("Bill", "Gates"), Arrays.asList("Mark", "Zuckerberg"));List<String> namesFlatStream = namesNested.stream().flatMap(Collection::stream).collect(Collectors.toList());
//        peek
//        skip
//        limit
//        Lazy Evaluation findFirst example (All intermediate operations are lazy)
//        sorted List<Employee> employees = empList.stream().sorted((e1, e2) -> e1.getName().compareTo(e2.getName())).collect(Collectors.toList());
//        min  Employee firstEmp = empList.stream().min((e1, e2) -> e1.getId() - e2.getId()).orElseThrow(NoSuchElementException::new);
//        max Employee maxSalEmp = empList.stream().max(Comparator.comparing(Employee::getSalary)).orElseThrow(NoSuchElementException::new);
//        distinct List<Integer> distinctIntList = intList.stream().distinct().collect(Collectors.toList());
//        allMatch returns boolean
//        anyMatch returns boolean
//        noneMatch returns boolean
//        ntList.stream().**Match(i -> i % 3 == 0);

//        Collectors
//        joining collect(Collectors.joining(", ")) string return kre ga
//        Collectors.toSet()
//        partitioningBy   Map<Boolean, List<Integer>> isEven = intList.stream().collect(Collectors.partitioningBy(i -> i % 2 == 0));
//        groupingBy  Map<Character, List<Employee>> groupByAlphabet = empList.stream().collect(Collectors.groupingBy(e -> new Character(e.getName().charAt(0))));
//        mapping  Map<Character, List<Integer>> idGroupedByAlphabet = empList.stream().collect(Collectors.groupingBy(e -> new Character(e.getName().charAt(0)), Collectors.mapping(Employee::getId, Collectors.toList())));
//        reducing  Double salIncrOverhead = empList.stream().collect(Collectors.reducing(0.0, e -> e.getSalary() * percentage / 100, (s1, s2) -> s1 + s2));
//        takeWhile (unique) vs filter (each one) java 9

//        FunctionalInterfaces
//        A functional interface is an interface that contains only one abstract method(unimplemented).
//        They can have only one functionality to exhibit
//        A functional interface can have any number of default methods
//        Before Java 8, we had to create anonymous inner class objects or implement these interfaces.
//        /* create anonymous inner class object */new Thread(new Runnable() {@Override public void run() { System.out.println("New thread created"); }}).start();
//        /* lambda expression to create the object */new Thread(()-> {System.out.println("New thread created");}).start(); }
//        BuiltIn ones
//        Function abstract method apply Function<Long, Long> adder = (value) -> value + 3;
//        Predicate abstract method test
//        BinaryOperator abstract method apply BinaryOperator<MyValue> binaryOperator = (value1, value2) -> { value1.add(value2); return value1; };
//        UnaryOperator takes a specific object as parameter, modifies that object, and returns it again UnaryOperator<Person> unaryOperator = (person) -> { person.name = "New Name"; return person; };
//        Supplier Supplier<Integer> supplier = () -> new Integer((int) (Math.random() * 1000D));
//        Consumer Consumer<Integer> consumer = (value) -> System.out.println(value);

    }
}
