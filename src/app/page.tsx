import { getAllPosts } from '@/lib/posts';
import HomeContent from './HomeContent';

export default function HomePage() {
  const posts = getAllPosts();
  return <HomeContent posts={posts} />;
}
