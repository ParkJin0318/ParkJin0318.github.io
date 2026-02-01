'use client';

interface PostTagsProps {
  tags: string[];
  selectedTag?: string | null;
  onClick?: (tag: string) => void;
  className?: string;
}

export default function PostTags({ tags, selectedTag, onClick, className = '' }: PostTagsProps) {
  return (
    <div className={`flex flex-wrap gap-2 ${className}`}>
      {tags.map((tag) => {
        const isSelected = tag === selectedTag;
        return (
          <span
            key={tag}
            onClick={() => onClick?.(tag)}
            className={`text-[0.9rem] rounded-[0.3rem] mr-1 px-2 py-1 ${
              onClick ? 'cursor-pointer' : ''
            } ${
              isSelected
                ? 'text-primary bg-primary-bg'
                : 'text-tag-text bg-tag-bg'
            }`}
          >
            #{tag}
          </span>
        );
      })}
    </div>
  );
}
