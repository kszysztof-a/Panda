package org.panda_lang.panda;

import org.panda_lang.core.work.Executable;
import org.panda_lang.core.work.ExecutableCell;
import org.panda_lang.core.work.Value;
import org.panda_lang.core.work.Wrapper;
import org.panda_lang.panda.lang.element.ClassPrototype;

import java.util.List;

public class PandaScript extends ClassPrototype implements Wrapper {

    public PandaScript() {

    }

    @Override
    public Value execute() {
        return null;
    }

    @Override
    public ExecutableCell addExecutable(Executable executable) {
        return null;
    }

    @Override
    public List<ExecutableCell> getExecutableCells() {
        return null;
    }

}
