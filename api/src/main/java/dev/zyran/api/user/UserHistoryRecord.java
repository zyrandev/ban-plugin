package dev.zyran.api.user;

import java.util.Map;
import java.util.UUID;

public interface UserHistoryRecord {

	String getId();

	UUID getUserId();

	String getAction();

	Map<String, Object> getMetadata();
}
