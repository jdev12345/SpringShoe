package org.springshoe;

import org.springshoe.services.ServiceA;
import org.springshoe.services.ServiceB;
import org.springshoe.services.ServiceC;

/*
*  plan is make an instance of a class A,B and C
* and inject them in order of
*
*       A
*      / \
*     B   C
*
*
**/
public class Main {
    static void main() {
        ServiceB serviceB = new ServiceB();
        ServiceC serviceC = new ServiceC();

        ServiceA serviceA = new ServiceA(serviceB, serviceC);
    }
}
