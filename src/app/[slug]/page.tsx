import { Metadata } from 'next';
import { notFound } from 'next/navigation';
import { getAllPosts, getPostBySlug } from '@/lib/posts';
import PostHeader from '@/components/post/PostHeader';
import MarkdownContent from '@/components/MarkdownContent';
import PostComments from '@/components/post/PostComments';

interface PostPageProps {
  params: Promise<{
    slug: string;
  }>;
}

export async function generateStaticParams() {
  const posts = getAllPosts();
  return posts.map((post) => ({
    slug: post.slug,
  }));
}

export async function generateMetadata({ params }: PostPageProps): Promise<Metadata> {
  const { slug } = await params;
  const post = getPostBySlug(slug);

  if (!post) {
    return {
      title: 'Not Found - Jin Blog',
    };
  }

  return {
    title: `${post.title} - Jin Blog`,
    description: post.description,
  };
}

export default async function PostPage({ params }: PostPageProps) {
  const { slug } = await params;
  const post = getPostBySlug(slug);

  if (!post) {
    notFound();
  }

  return (
    <article className="w-full max-w-[50rem] mx-auto px-6 py-12">
      <PostHeader
        title={post.title}
        createdAt={post.createdAt}
      />

      <MarkdownContent content={post.content} />

      <PostComments />
    </article>
  );
}
