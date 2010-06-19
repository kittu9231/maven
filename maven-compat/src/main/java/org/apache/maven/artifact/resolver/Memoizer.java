package org.apache.maven.artifact.resolver;

import java.util.concurrent.*;

/**
 * Memoizer
 * <p/>
 * Final implementation of Memoizer
 * <p/>
 * Licensed under http://creativecommons.org/licenses/publicdomain
 *
 * @author Brian Goetz and Tim Peierls
 */
public class Memoizer<A, V>
    implements Computable<A, V>
{
    private final ConcurrentMap<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();

    private final Computable<A, V> c;

    public Memoizer( Computable<A, V> c )
    {
        this.c = c;
    }

    public V compute( final A arg )
        throws InterruptedException
    {
        while ( true )
        {
            Future<V> f = cache.get( arg );
            if ( f == null )
            {
                Callable<V> eval = new Callable<V>()
                {
                    public V call()
                        throws InterruptedException
                    {
                        return c.compute( arg );
                    }
                };
                FutureTask<V> ft = new FutureTask<V>( eval );
                f = cache.putIfAbsent( arg, ft );
                if ( f == null )
                {
                    f = ft;
                    ft.run();
                }
            }
            try
            {
                return f.get();
            }
            catch ( CancellationException e )
            {
                cache.remove( arg, f );
            }
            catch ( ExecutionException e )
            {
                throw launderThrowable( e.getCause() );
            }
        }
    }

    private static RuntimeException launderThrowable( Throwable t )
    {
        if ( t instanceof RuntimeException )
        {
            return (RuntimeException) t;                // Line #1
        }
        else if ( t instanceof Error )
        {
            throw (Error) t;                                    // Line #2
        }
        else
        {
            throw new IllegalStateException( "Not unchecked", t );
        }// Line #3
    }

}