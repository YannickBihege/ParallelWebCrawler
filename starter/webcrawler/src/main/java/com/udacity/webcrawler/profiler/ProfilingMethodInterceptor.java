package com.udacity.webcrawler.profiler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.time.Clock;
import java.util.Objects;

// 09.04
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;

/**
 * A method interceptor that checks whether {@link Method}s are annotated with the {@link Profiled}
 * annotation. If they are, the method interceptor records how long the method invocation took.
 */
final class ProfilingMethodInterceptor implements InvocationHandler {

    private final Clock clock;
    private final Object target;
    private final ProfilingState state;
    private final ZonedDateTime timeStart;

    // XXX: You will need to add more instance fields and constructor arguments to this class.
    ProfilingMethodInterceptor(Clock clock, Object target, ProfilingState state, ZonedDateTime timeStart) {
        this.clock = Objects.requireNonNull(clock);
       // this.target = Objects.requireNonNull(target);
      //  this.state = Objects.requireNonNull(state);
       // this.timeStart = Objects.requireNonNull(timeStart);
        this.target = target;
        this.state = state;
        this.timeStart = timeStart;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // XX: This method interceptor should inspect the called method to see if it is a profiled
        //       method. For profiled methods, the interceptor should record the start time, then
        //       invoke the method using the object that is being profiled. Finally, for profiled
        //       methods, the interceptor should record how long the method call took, using the
        //       ProfilingState methods.
        Object invoked = null;
        Instant start = null;
        boolean profiled = method.getAnnotation(Profiled.class) != null;
        if (profiled) {
            start = clock.instant();
        }
        try {
            invoked = method.invoke(target, args);
        } catch (InvocationTargetException invocationTargetException) {
            throw invocationTargetException.getTargetException();
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            if (profiled) {
                Duration duration = Duration.between(start, clock.instant());
                state.record(target.getClass(), method, duration);
            }
        }
        return invoked;

    }

    /*
    @Override
    public boolean equals(Object object){

        if(object == this) return true;
        if(!(object instanceof ProfilingMethodInterceptor)) return false;
        ProfilingMethodInterceptor methodInterceptor = (ProfilingMethodInterceptor) object;
        return Objects.equals(this.clock, methodInterceptor.clock) && Objects.equals(this.target,methodInterceptor.target) && Objects.equals(this.state,methodInterceptor.state) && Objects.equals(this.timeStart,methodInterceptor.timeStart);

    }
    */

}
