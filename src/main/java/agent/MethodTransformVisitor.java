package agent; 

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

class MethodTransformVisitor extends MethodVisitor implements Opcodes {

	String mName;
    int line;

    public MethodTransformVisitor(final MethodVisitor mv, String name) {
        super(ASM5, mv);
        this.mName = name;
    }

    // statement coverage collection
    @Override
    public void visitLineNumber(int line, Label start) {
        if(line != 0)
        {
        	this.line = line;
            
            mv.visitLdcInsn(mName);
            mv.visitLdcInsn(line);
        	mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
            mv.visitMethodInsn(INVOKESTATIC, "agent/CollectCoverage", "addCoveredLine", "(Ljava/lang/String;Ljava/lang/Integer;)V", false);
        }
        
        super.visitLineNumber(line, start);
    }

    // visit a label
    @Override
    public void visitLabel(Label label) {
    	
    	if(line != 0)
        {
            mv.visitLdcInsn(mName);
            mv.visitLdcInsn(line);
        	mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
            mv.visitMethodInsn(INVOKESTATIC, "agent/CollectCoverage", "addCoveredLine", "(Ljava/lang/String;Ljava/lang/Integer;)V", false);
        }
        
        super.visitLabel(label);
    }
    @Override
    public void visitEnd() {

        super.visitEnd();
    }

}

