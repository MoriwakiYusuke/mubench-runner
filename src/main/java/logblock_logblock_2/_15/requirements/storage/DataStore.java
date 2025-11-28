package logblock_logblock_2._15.requirements.storage;

import lombok.Getter;
import logblock_logblock_2._15.requirements.LogBlockPlugin;
import logblock_logblock_2._15.requirements.entry.AbstractEntry;
import logblock_logblock_2._15.requirements.entry.EntryManager;
import logblock_logblock_2._15.requirements.query.Query;

import java.util.List;
import java.util.logging.Logger;

public abstract class DataStore implements EntryManager
{
    @Getter
    private static DataStore instance;
    protected LogBlockPlugin lb;

    public DataStore(LogBlockPlugin lb) {
        this.lb = lb;
        instance = this;
    }

    public Logger getLogger() {
        return this.lb.getLogger();
    }

    public void write(AbstractEntry entry)
    {
        throw new IllegalStateException("Cannot write an abstract entry");
    }

    public abstract List<AbstractEntry> retrieveEntries(Query query);

    public abstract void onDisable();
}
