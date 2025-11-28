package logblock_logblock_2._15.requirements.entry;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class AbstractEntry implements Serializable
{

    private int id = -1;

    public AbstractEntry()
    {
    }

    public AbstractEntry(int id)
    {
        this.id = id;
    }
}
