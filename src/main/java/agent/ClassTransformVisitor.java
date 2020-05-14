package agent;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ClassTransformVisitor extends ClassVisitor implements Opcodes {
	
	protected String cName;

	public ClassTransformVisitor(ClassWriter writer) {
        super(Opcodes.ASM5, writer);
    }

	@Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.cName = name;

        super.visit(version, access, name, signature, superName, interfaces);
    }
    
    @Override
    public MethodVisitor visitMethod(final int access, final String name,
            final String desc, final String signature, final String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        return mv == null ? null : new MethodTransformVisitor(mv, cName);
    }
    
}