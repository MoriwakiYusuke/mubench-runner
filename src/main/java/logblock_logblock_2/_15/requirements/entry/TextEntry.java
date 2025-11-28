package logblock_logblock_2._15.requirements.entry;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class TextEntry extends AbstractEntry
{

    private final String text;

    public TextEntry(int id, String text)
    {
        super(id);
        this.text = text;
    }
}
