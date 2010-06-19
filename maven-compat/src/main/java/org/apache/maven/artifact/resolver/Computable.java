package org.apache.maven.artifact.resolver;

/**
 *
*/
public interface Computable<T,V> {
     V compute(T t) throws InterruptedException;
}
