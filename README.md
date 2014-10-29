jsonserializer
==========

This is my attempt at a serializer to serialize POJOs to JSON even if there are circular references in the Object graph.

Currently suppports serialization
* All basic JavaTypes
* Enums (In a crude way).
* Date - depends on Jackson
* Arrays
* Collections


Please note: This is at a very beginning stage. It will have bugs. Hopefully I will be able to spend more time on this.
