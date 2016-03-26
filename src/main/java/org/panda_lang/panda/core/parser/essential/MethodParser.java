package org.panda_lang.panda.core.parser.essential;

import org.panda_lang.panda.Panda;
import org.panda_lang.panda.core.Alice;
import org.panda_lang.panda.core.parser.Atom;
import org.panda_lang.panda.core.parser.PandaException;
import org.panda_lang.panda.core.parser.Parser;
import org.panda_lang.panda.core.parser.ParserLayout;
import org.panda_lang.panda.core.parser.essential.assistant.MethodAssistant;
import org.panda_lang.panda.core.parser.essential.util.EssentialPriority;
import org.panda_lang.panda.core.parser.essential.util.MethodInfo;
import org.panda_lang.panda.core.syntax.*;
import org.panda_lang.panda.core.syntax.Runtime;
import org.panda_lang.panda.core.syntax.block.MethodBlock;

public class MethodParser implements Parser {

    public static void initialize(Panda panda) {
        MethodParser methodParser = new MethodParser();
        ParserLayout parserLayout = new ParserLayout(methodParser, "*(*);", EssentialPriority.METHOD.getPriority());
        panda.getPandaCore().registerParser(parserLayout);
    }

    @Override
    public Runtime parse(final Atom atom) {
        final String source = atom.getSourcesDivider().getLine();
        final MethodInfo mi = MethodAssistant.getMethodIndication(atom, source);

        if (mi == null) {
            System.out.println("[MethodParser] Indication failed");
            return null;
        }

        // {method.external}
        if (mi.isExternal()) {

            // {method.static}
            if (mi.isStatic()) {
                final Vial vial = mi.getVial();
                return new Runtime(new Method(mi.getMethodName(), new Executable() {
                    @Override
                    public Essence run(Alice alice) {
                        Alice fork = alice
                                .fork()
                                .pandaScript(atom.getPandaScript())
                                .factors(mi.getFactors());
                        return vial.call(mi.getMethodName(), fork);
                    }
                }));

                // {instance.method}
            }
            else {
                final Factor instance = mi.getInstance();

                if (instance == null) {
                    PandaException exception = new PandaException("MethodParserException: Instance not found", atom.getSourcesDivider());
                    return atom.getPandaParser().throwException(exception);
                }

                String instanceOf = instance.getDataType();

                // {instance.type.defined}
                if (instanceOf != null) {
                    Vial vial = atom.getDependencies().getVial(instanceOf);
                    final Method method = vial.getMethod(mi.getMethodName());

                    if (method == null) {
                        PandaException exception = new PandaException("MethodParserException: Method not found", atom.getSourcesDivider());
                        return atom.getPandaParser().throwException(exception);
                    }

                    return new Runtime(instance, new Executable() {
                        @Override
                        public Essence run(Alice alice) {
                            alice.setInstance(instance);
                            Essence essence = instance.getValue(alice);
                            alice = essence.particle(alice);
                            return method.run(alice);
                        }
                    }, mi.getFactors());
                }

                // {instance.type.undefined}
                return new Runtime(instance, new Executable() {
                    @Override
                    public Essence run(Alice alice) {
                        alice.setInstance(instance);
                        Essence essence = instance.getValue(alice);
                        String methodName = mi.getMethodName();
                        return essence.call(methodName, alice);
                    }
                }, mi.getFactors());
            }

            // {local}
        }
        else {
            return new Runtime(new Method(mi.getMethodName(), new Executable() {
                @Override
                public Essence run(Alice alice) {
                    return atom.getPandaScript().call(MethodBlock.class, mi.getMethodName(), mi.getFactors());
                }
            }));
        }
    }

}
