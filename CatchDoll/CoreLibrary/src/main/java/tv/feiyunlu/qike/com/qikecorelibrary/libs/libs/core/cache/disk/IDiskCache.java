/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.core.cache.disk;


import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.core.cache.CacheEntry;

/**
 * An interface for a cache keyed by a String with a byte array as data.
 */
public interface IDiskCache {
    /**
     * Retrieves an entry from the cache.
     * @param key Cache key
     * @return An {@link Entry} or null in the event of a cache miss
     */
    public CacheEntry get(String key);

    public boolean containsKey(String key);
    
    /**
     * Adds or replaces an entry to the cache.
     * @param key Cache key
     * @param entry Data to store and metadata for cache coherency, TTL, etc.
     */
    public void put(String key, CacheEntry entry);

    /**
     * Performs any potentially long-running actions needed to initialize the cache;
     * will be called from a worker thread.
     */
    public void initialize();

    /**
     * Removes an entry from the cache.
     * @param key Cache key
     */
    public void remove(String key);

    /**
     * Empties the cache.
     */
    public void clear();

  

}
