package producerconsumer;

import com.google.common.base.MoreObjects;
import net.jcip.annotations.ThreadSafe;

import java.util.Random;
import java.util.UUID;

/**
 * Created by kennylbj on 16/9/10.
 * Item is a element of Buffer.
 */
@ThreadSafe
public final class Item {
    private final String itemId;
    private final String itemName;
    private static final Random random = new Random(System.nanoTime());
    private static final String ITEM_NAME[] = {
            "apple",
            "banana",
            "tomato",
            "watermelon"
    };

    private Item(String itemId, String itemName) {
        this.itemId = itemId;
        this.itemName = itemName;
    }

    public static Item generate() {
        String uuid = UUID.randomUUID().toString();
        String name = ITEM_NAME[random.nextInt(ITEM_NAME.length)];
        return new Item(uuid, name);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("itemId", itemId)
                .add("itemName", itemName)
                .toString();
    }

}
