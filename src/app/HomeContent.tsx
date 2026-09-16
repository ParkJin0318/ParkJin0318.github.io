'use client';

import { useState, useMemo } from 'react';
import { useRouter } from 'next/navigation';
import TabRow from '@/components/TabRow';
import PostCard from '@/components/post/PostCard';
import { Post } from '@/types/post';

interface HomeContentProps {
  posts: Post[];
}

export default function HomeContent({ posts }: HomeContentProps) {
  const router = useRouter();

  const categories = useMemo(() => {
    return [...new Set(posts.map((post) => post.category))];
  }, [posts]);

  const [selectedCategory, setSelectedCategory] = useState<string | null>(
    categories[0] || null
  );

  const filteredPosts = useMemo(() => {
    return posts.filter((post) => post.category === selectedCategory);
  }, [posts, selectedCategory]);

  const handleCategorySelect = (category: string) => {
    setSelectedCategory(category);
  };

  return (
    <div className="w-full max-w-[50rem] mx-auto px-6 py-12">
      <TabRow
        tabs={categories}
        selectedTab={selectedCategory}
        onTabSelected={handleCategorySelect}
      />

      <div className="flex flex-col">
        {filteredPosts.map((post) => (
          <PostCard
            key={post.slug}
            post={post}
            onClick={() => router.push(`/${post.slug}`)}
          />
        ))}
      </div>
    </div>
  );
}
