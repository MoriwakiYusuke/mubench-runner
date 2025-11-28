package logblock_logblock_2._15.requirements;

import logblock_logblock_2._15.requirements.storage.DataStore;

import java.util.logging.Logger;

public interface LogBlockPlugin
{
    public abstract DataStore getDataStore();

    public abstract Logger getLogger();
}
