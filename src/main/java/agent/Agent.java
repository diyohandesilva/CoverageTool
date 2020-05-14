package agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import org.junit.runner.JUnitCore;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

public class Agent {
    public static void premain(String agentArgs, Instrumentation inst) {


        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader classLoader, String s, Class<?> aClass,
                    ProtectionDomain protectionDomain, byte[] bytes) throws IllegalClassFormatException {

                if (s.startsWith("org/apache/commons/dbutils") ||
                        s.startsWith("org/joda/time") ||
                        "project/Stuff".equals(s)) {
                    // ASM Code
                    System.out.println("Java Agent is executing\n");


                    System.out.println("Junit Lister is added\n");
                    ClassReader reader = new ClassReader(bytes);
                    ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
                    System.out.println("CLass file I think:");
                    ClassTransformVisitor cVisitor = new ClassTransformVisitor(writer);
                    reader.accept(cVisitor, 0);
                    return writer.toByteArray();

                }

                return bytes;
            }
        });
    }
}