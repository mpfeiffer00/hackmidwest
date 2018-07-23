package shareit.api.external.search;

/**
 * @author amcginn
 */
public class SearchRequest
{
    private String base64Image;
    private final String language = "eng";
    private final String scale = "true";
    private final String url = "";// "http://dl.a9t9.com/ocrbenchmark/eng.png";
    private final String FileType = ".Auto";

    /**
     * @return the base64ImageType
     */
    public String getFileType()
    {
        return FileType;
    }

    /**
     * @return the url
     */
    public String getUrl()
    {
        return url;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + (base64Image == null ? 0 : base64Image.hashCode());
        result = prime * result + (language == null ? 0 : language.hashCode());
        result = prime * result + (scale == null ? 0 : scale.hashCode());
        result = prime * result + (url == null ? 0 : url.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final SearchRequest other = (SearchRequest) obj;
        if (base64Image == null)
        {
            if (other.base64Image != null)
            {
                return false;
            }
        } else
            if (!base64Image.equals(other.base64Image))
            {
                return false;
            }
        if (language == null)
        {
            if (other.language != null)
            {
                return false;
            }
        } else
            if (!language.equals(other.language))
            {
                return false;
            }
        if (scale == null)
        {
            if (other.scale != null)
            {
                return false;
            }
        } else
            if (!scale.equals(other.scale))
            {
                return false;
            }
        if (url == null)
        {
            if (other.url != null)
            {
                return false;
            }
        } else
            if (!url.equals(other.url))
            {
                return false;
            }
        return true;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "SearchRequest [base64Image=" + base64Image + ", language=" + language + ", scale=" + scale + ", url=" + url + "]";
    }

    public SearchRequest()
    {
    }

    public SearchRequest(final String base64Image)
    {
        this.base64Image = base64Image;
    }

    /**
     * @return the base64Image
     */
    public String getBase64Image()
    {
        return base64Image;
    }

    /**
     * @param base64Image
     *            the base64Image to set
     */
    public void setBase64Image(final String base64Image)
    {
        this.base64Image = base64Image;
    }

    /**
     * @return the language
     */
    public String getLanguage()
    {
        return language;
    }

    /**
     * @return the scale
     */
    public String getScale()
    {
        return scale;
    }
}
