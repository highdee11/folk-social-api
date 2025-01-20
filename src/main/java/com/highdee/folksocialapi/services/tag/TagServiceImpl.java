package com.highdee.folksocialapi.services.tag;

import com.highdee.folksocialapi.models.post.Tag;
import com.highdee.folksocialapi.repositories.post.TagRepository;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService{

    private TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Tag> createAllTags(List<String> names) {
        return names.stream().map(this::createTag).toList();
    }

    @Override
    public Tag createTag(String name) {
        String normalisedName = normalizeTag(name);
        Tag tag = new Tag();

        // check if tag already exist
        Optional<Tag> checkTag = tagRepository.findByName(normalisedName);

        // create new tag in db
        if(checkTag.isEmpty()) {
            Tag newTag = new Tag();
            newTag.setName(normalisedName);
            tag = tagRepository.save(newTag);
        }else{
            tag = checkTag.get();
        }

        return tag;
    }

    @Override
    public String normalizeTag(String tagName) {
        if (tagName == null || tagName.isEmpty()) {
            throw new IllegalArgumentException("Tag name cannot be null or empty");
        }

        // Convert to lowercase
        String normalized = tagName.toLowerCase().trim();

        // Replace multiple spaces with a single space
        normalized = normalized.replaceAll("\\s+", " ");

        // Replace spaces with hyphens (optional, depending on your use case)
        normalized = normalized.replace(" ", "-");

        // Remove accents/diacritics (optional, for internationalization support)
        normalized = Normalizer.normalize(normalized, Normalizer.Form.NFD);
        normalized = normalized.replaceAll("\\p{M}", "");

        // Remove special characters (optional, to allow only alphanumeric and hyphens)
        normalized = normalized.replaceAll("[^a-z0-9-]", "");

        return normalized;
    }
}
