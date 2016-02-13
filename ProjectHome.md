Wobly is efficient, fast, flexible and backward and forward compatible serialization protocol for Java.

Wobly was created in Wowd, built from a need to have a fast, easy to use serialization protocol that can handle constant modifications and constant communication between systems running different versions of code. It is used for all objects and communication in Wowd's distributed system. (the name Wobly comes from Wowd's byte-level serialization). Its implementation started in 2008, and so far it matured, and in Wowd more than 400 objects are being serialized by it.


### Main features: ###
  * generated serialization code is very fast and serialized objects are very small (using efficient binary format)
  * allows full backward and forward compatibility. Even new fields when stored in old code are not lost a can be accessed with new code later. This is accomplished with very little overhead, which can be tuned/turned off if extremly small byte representation is needed instead
  * Java native - everything is written, defined and generated in Java. Via annotations needed information is defined, which are then processed, and readable java code for serialization is generated. Classes that use serialization can be changed and have logic and methods (in summary, no need for definition files, no need for POJO only classes, etc)
  * supports very complex java objects, and understands generics. With simple `@WoblyField` annotation you can serialize fields like `Map<String, List<Map<Integer, OtherObject>>>`.
  * supports (complex) object hierarchy. For example, you can have C -> B -> A and D -> A (where X -> Y means X extends Y) that are all serialized, and A is abstract. You can then deserialize any of those objects by only knowing that it is subclass of A, and not what specific subclass it is.
  * out of the box support for trove - efficient primitive collections

It is somewhat similar to other open source serialization protocols, like protocol buffers. Though it only supports Java, it has much deeper integration with it - so if you are using only Java, it is a strength, and not a weakness.
You can check out how great performance is in the common benchmark for serializers: https://github.com/eishay/jvm-serializers/wiki


### Usage: ###

Easiest way to serialize a class is to have it extend `WoblyImpl` and have `@WoblyField` annotation on each of it's fields (with different id for each field). For example:

```
class City extends WoblyImpl {
  @WoblyField(id = 0)
  String name;

  @WoblyField(id = 1)
  long population;

}
```

Fields can be any combination of primitive classes (byte, short, char, int, long, float, double and their Object counterparts), Strings, enums, arrays, collections, sets, maps and user objects that are serialized via Wobly.

```
class Person extends WoblyImpl {
  @WoblyField(id = 0)
  String name;

  @WoblyField(id = 1)
  String email;

  @WoblyField(id = 2)
  List<int[]> phoneNumbers;

  @WoblyField(id = 3)
  Map<Long, City> visited;
}  
```

You'll have to download wobly-core and place it in your classpath to get all common classes and annotations (like `WoblyImpl` and `WoblyField`). For generating code you would then need wobly-generation.

### Generating code: ###

When you've created and annotated classes you want to serialize, you should call `WoblyGenerator.updateInSourceFolder(sourceFolder)`. Or if you want to restrict to some package only - use `WoblyGenerator.updateInPackage(sourceFolder, package)`.
Or you can execute wobly-generation jar, and passing it first command line argument for sourceFolder, and second optionally for package name.


