package weiboclient4j;

import weiboclient4j.model.Comment;
import weiboclient4j.model.CommentList;
import hoverruan_weiboclient4j._128.params.Cid;
import hoverruan_weiboclient4j._128.params.CommentOri;
import hoverruan_weiboclient4j._128.params.CommentParam;
import hoverruan_weiboclient4j._128.params.FilterByAuthor;
import hoverruan_weiboclient4j._128.params.FilterBySource;
import hoverruan_weiboclient4j._128.params.Id;
import hoverruan_weiboclient4j._128.params.Paging;
import hoverruan_weiboclient4j._128.params.WithoutMention;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Hover Ruan
 */
public class CommentService extends AbstractService {

    public CommentService(WeiboClient client) {
        super(client);
    }

    public CommentList getComments(Id id) throws WeiboClientException {
        return getComments(id, Paging.EMPTY);
    }

    public CommentList getComments(Id id, Paging paging) throws WeiboClientException {
        return getComments(id, paging, FilterByAuthor.All);
    }

    public CommentList getComments(Id id, Paging paging, FilterByAuthor filterByAuthor) throws WeiboClientException {
        return doGet("comments/show",
                paging, withParams(id, filterByAuthor), CommentList.class);
    }

    public CommentList getCommentsByMe() throws WeiboClientException {
        return getCommentsByMe(Paging.EMPTY);
    }

    public CommentList getCommentsByMe(Paging paging) throws WeiboClientException {
        return getCommentsByMe(paging, FilterByAuthor.All);
    }

    public CommentList getCommentsByMe(Paging paging, FilterByAuthor filterByAuthor) throws WeiboClientException {
        return doGet("comments/by_me",
                paging, withParams(filterByAuthor), CommentList.class);
    }

    public CommentList getCommentsToMe() throws WeiboClientException {
        return getCommentsToMe(Paging.EMPTY);
    }

    public CommentList getCommentsToMe(Paging paging) throws WeiboClientException {
        return getCommentsToMe(paging, FilterByAuthor.All, FilterBySource.All);
    }

    public CommentList getCommentsToMe(Paging paging, FilterByAuthor filterByAuthor, FilterBySource filterBySource)
            throws WeiboClientException {
        return doGet("comments/to_me",
                paging, withParams(filterByAuthor, filterBySource), CommentList.class);
    }

    public CommentList getCommentsTimeline() throws WeiboClientException {
        return getCommentsTimeline(Paging.EMPTY);
    }

    public CommentList getCommentsTimeline(Paging paging) throws WeiboClientException {
        return doGet("comments/timeline", paging, CommentList.class);
    }

    public CommentList getMentionsComments() throws WeiboClientException {
        return getMentionsComments(Paging.EMPTY);
    }

    public CommentList getMentionsComments(Paging paging) throws WeiboClientException {
        return getMentionsComments(paging, FilterByAuthor.All, FilterBySource.All);
    }

    public CommentList getMentionsComments(Paging paging, FilterByAuthor filterByAuthor, FilterBySource filterBySource)
            throws WeiboClientException {
        return doGet("comments/mentions",
                paging, withParams(filterByAuthor, filterBySource), CommentList.class);
    }

    public List<Comment> getCommentsBatch(Collection<Cid> cids) throws WeiboClientException {
        return doGet("comments/show_batch",
                withParams(Cid.cidsParam(cids)), Comment.TYPE_COMMENT_LIST);
    }

    public Comment createComment(Id id, String comment) throws WeiboClientException {
        return createComment(id, comment, CommentOri.No);
    }

    public Comment createComment(Id id, CommentParam comment, CommentOri commentOri) throws WeiboClientException {
        return doPost("comments/create",
                withParams(id, comment, commentOri), Comment.class);
    }

    public Comment createComment(Id id, String comment, CommentOri commentOri) throws WeiboClientException {
        return createComment(id, new CommentParam(comment), commentOri);
    }

    public Comment destroyComment(Cid cid) throws WeiboClientException {
        return doPost("comments/destroy", withParams(cid), Comment.class);
    }

    public List<Comment> destroyCommentBatch(Collection<Cid> cids) throws WeiboClientException {
        List<Id> ids = new ArrayList<Id>(cids.size());
        for (Cid cid : cids) {
            ids.add(new Id(cid.getValue()));
        }

        return doPost("comments/destroy_batch",
                withParams(Id.idsParam(ids)), Comment.TYPE_COMMENT_LIST);
    }

    public Comment replyComment(Id id, Cid cid, String comment) throws WeiboClientException {
        return replyComment(id, cid, comment, WithoutMention.No, CommentOri.No);
    }

    public Comment replyComment(Id id, Cid cid, String comment, WithoutMention withoutMention, CommentOri commentOri)
            throws WeiboClientException {
        return replyComment(id, cid, new CommentParam(comment), withoutMention, commentOri);
    }

    public Comment replyComment(Id id, Cid cid, CommentParam comment, WithoutMention withoutMention,
                                CommentOri commentOri) throws WeiboClientException {
        return doPost("comments/reply",
                withParams(id, cid, comment, withoutMention, commentOri), Comment.class);
    }
}
